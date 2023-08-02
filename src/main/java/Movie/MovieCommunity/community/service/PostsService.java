package Movie.MovieCommunity.community.service;


import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.awsS3.domain.repository.GalleryRepository;
import Movie.MovieCommunity.awsS3.service.GalleryService;
import Movie.MovieCommunity.community.dto.PostsDto;
import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.in;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final GalleryRepository galleryRepository;
    private final MovieRepository movieRepository;
    private final GalleryService galleryService;


    /* READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선 */
    @Transactional(readOnly = true)
    public PostsDto.Response findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));


        return new PostsDto.Response(posts);
    }

    /* UPDATE (dirty checking 영속성 컨텍스트)
     *  User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
     * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다. */
    @Transactional
    public void update(Long id, PostsDto.RequestParam dto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        List<GalleryEntity> galleries = posts.getGalleries();
        if (galleries != null){
            for (GalleryEntity gallery : galleries) {
                if(dto.getGalleryIds().isPresent()) {
                    if (!dto.getGalleryIds().get().contains(gallery.getId())) {
                        galleryService.delete(gallery.getId());
                    }
                }
                else{
                    galleryService.delete(gallery.getId());
                }
            }
        }
        if(dto.getGalleryIds().isPresent()) {
            List<Long> ids = dto.getGalleryIds().get();
            List<GalleryEntity> galleryList = new ArrayList<>();

            for (Long gId: ids) {
                GalleryEntity gallery = galleryRepository.findById(gId).get();
                galleryRepository.updatePosts(gId,posts);
                galleryList.add(gallery);
            }
        }
        Movie movie = movieRepository.findById(dto.getMovieId()).get();
        posts.update(dto.getTitle(), movie, dto.getContent());
    }

    /* DELETE */
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        postsRepository.delete(posts);
    }

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return postsRepository.updateView(id);
    }

    /* Paging and Sort */
    @Transactional(readOnly = true)
    public Page<Posts> pageList(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    /* search */
    @Transactional(readOnly = true)
    public Page<Posts> search(String keyword, Pageable pageable) {
        Page<Posts> postsList = postsRepository.findByTitleContaining(keyword, pageable);
        return postsList;
    }


}

