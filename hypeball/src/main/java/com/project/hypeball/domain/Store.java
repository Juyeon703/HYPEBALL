package com.project.hypeball.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hypeball.dto.StoreSaveForm;
import com.project.hypeball.dto.StoreUpdateForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate // 변경된 필드만 update
public class Store implements Serializable {
    // 가게 설명 혹은 종류 필드 추가

    @Id //PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 자동증가
    @Column(name = "store_id")
    private Long id;

    @NotNull
    @Column(length = 10)
    private String name; // 상호명

    @Column(length = 10)
    private String branch; // 지점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String menu; // 추천메뉴

    @Column
    private String content; // 소개

    @NotNull
    @Column
    private String address; // 주소

    @NotNull
    @Column
    private Double lat; // 위도

    @NotNull
    @Column
    private Double lng; // 경도

    @JsonIgnore
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @ColumnDefault("0") //script 생성시 적용
    private int totalLikeCount; // 좋아요 갯수

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_rating_id")
    private StarRating starRating;

    public static void calcStarAvg(Store store, Review review) {
        store.setStarRating(StarRating.updateStarRating(store.getStarRating(), review.getStar()));
    }

    public static Store createStore(StoreSaveForm form) {
        Store store = new Store();
        store.setName(form.getName());
        store.setBranch(form.getBranch());
        store.setCategory(form.getCategory());
        store.setMenu(form.getMenu());
        store.setContent(form.getContent());
        store.setAddress(form.getAddress());
        store.setLat(form.getLat());
        store.setLng(form.getLng());
        store.setTotalLikeCount(0);
        store.setStarRating(StarRating.createStarRating());
        return store;
    }

    public void updateStore(Store store, StoreUpdateForm form) {
        store.setId(form.getId());
        store.setName(form.getName());
        store.setBranch(form.getBranch());
        store.setCategory(form.getCategory());
        store.setMenu(form.getMenu());
        store.setContent(form.getContent());
        store.setAddress(form.getAddress());
        store.setLat(form.getLat());
        store.setLng(form.getLng());
    }

    public static int addCount(Store store) {
        store.totalLikeCount += 1;
        return store.getTotalLikeCount();
    }

    public static int removeCount(Store store) throws Exception {
        int restCount = store.totalLikeCount - 1;
        if (restCount < 0) {
            throw new Exception();
        }
        store.totalLikeCount = restCount;
        return store.getTotalLikeCount();
    }
}
