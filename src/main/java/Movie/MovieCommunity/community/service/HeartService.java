package Movie.MovieCommunity.community.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.domain.Heart;
import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.dto.HeartRequestDTO;
import Movie.MovieCommunity.community.repository.HeartRepository;
import Movie.MovieCommunity.community.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public void insert(HeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("Could not found board id : " + heartRequestDTO.getPostsId()));

        // 이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByMemberAndPosts(member, posts).isPresent()){
            throw new Exception();
        }


        Heart heart = Heart.builder()
                .posts(posts)
                .member(member)
                .build();
        postsRepository.updateAddCount(posts);
        heartRepository.save(heart);
    }

    @Transactional
    public void delete(HeartRequestDTO heartRequestDTO) {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("Could not found board id : " + heartRequestDTO.getPostsId()));

        Heart heart = heartRepository.findByMemberAndPosts(member, posts)
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));
        postsRepository.updateSubtractCount(posts);
        heartRepository.delete(heart);
    }
}