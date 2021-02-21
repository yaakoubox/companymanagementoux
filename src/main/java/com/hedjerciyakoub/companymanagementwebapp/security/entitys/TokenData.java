package com.hedjerciyakoub.companymanagementwebapp.security.entitys;


import com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="token_data" )
public class TokenData {

    @Id
    @GeneratedValue(generator = "token_data")
    @GenericGenerator(name="token_data" ,
            strategy = "com.hedjerciyakoub.companymanagementwebapp.generators.AsecGenerator",
            parameters = {
                    @Parameter(name= AsecGenerator.TABLE_NAME,value = "token_data"),
                    @Parameter(name=AsecGenerator.ID_NAME,value = "token_id")
            })
    @Column(name="token_id")
    private int tokenId;

    @Column(name = "token" , nullable = false)
    private String token;

    @Column(name = "create_time" , nullable = false)
    private LocalDateTime createTime;

    @Column(name = "expire_time" , nullable = false)
    private LocalDateTime expireTime;

    @Column(name = "confirm_time")
    private LocalDateTime confirmTime;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name="username")
    private AppUser user;

    public TokenData(String token, LocalDateTime createTime, LocalDateTime expireTime) {
        this.token = token;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "tokenId=" + tokenId +
                ", token='" + token + '\'' +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", confirmTime=" + confirmTime +
                '}';
    }
}
