package com.example.policycrud.models.entites;



import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class AbstractEntity {
    @Column(name = "creation_date", nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "modification_date")
    @LastModifiedDate
    private LocalDateTime modificationDate;

    @PrePersist
    void set(){
        this.creationDate = LocalDateTime.now();
    }


}
