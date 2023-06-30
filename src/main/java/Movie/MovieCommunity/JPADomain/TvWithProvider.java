package Movie.MovieCommunity.JPADomain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name="tvwithprovider")
@Entity(name = "tvwithprovider")
@NoArgsConstructor
public class TvWithProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tv_with_provider_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_id")
    private Tv tv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private TvProvider tvProvider;


    public TvWithProvider(Tv tv, TvProvider tvProvider) {
        this.tv = tv;
        this.tvProvider = tvProvider;
    }
}