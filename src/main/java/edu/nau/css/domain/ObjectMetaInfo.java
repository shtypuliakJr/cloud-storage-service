package edu.nau.css.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "objects")
public class ObjectMetaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "object_name", nullable = false)
    private String objectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_object_id")
    private ObjectMetaInfo parentObject;

    @Column(name = "file_type", nullable = true)
    private String fileType;

    @Column(name = "is_folder", nullable = false)
    private Boolean isFolder;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "parentObject", fetch = FetchType.LAZY)
    private List<ObjectMetaInfo> childrenObjects = new ArrayList<>();

}
