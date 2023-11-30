package com.example.capstone.model.Veterinarian;


import com.example.capstone.model.User.User;
import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Setter
@Getter
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vetId;
    private String vetName;
    private String vetLicenseNumber; // giấy phép thú y - định dạng bằng ảnh
    private String vetAddress;
    private String vetPhone;
    private String description;
    private String specialize; //chuyên môn
    private Boolean status;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
