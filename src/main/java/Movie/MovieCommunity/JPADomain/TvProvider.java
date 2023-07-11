package Movie.MovieCommunity.JPADomain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name="tv_provider")
@Entity
@SequenceGenerator(
        name="PROVIDER_SEQ_GENERATOR",
        sequenceName="PROVIDER_SEQ",
        initialValue=1
)
public class TvProvider {

    @Id
    @Column(name = "provider_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE
            ,generator="PROVIDER_SEQ_GENERATOR"
    )
    private Long id;

    @Column(name = "provider_name")
    private String providerNm;

    @Column(name = "logo_path",columnDefinition = "TEXT")
    private String logoPath;

    @Column(name = "provider_tm_id")
    private Long providerTmId;

    protected TvProvider(){
    }

    public TvProvider(Long providerTmId, String providerNm, String logoPath) {
        this.providerTmId = providerTmId;
        this.providerNm = providerNm;
        this.logoPath = logoPath;
    }

    @Override
    public String toString() {
        return "JpaTvProvider{" +
                "id=" + id +
                ", providerNm='" + providerNm + '\'' +
                ", logoPath='" + logoPath + '\'' +
                '}';
    }
}
