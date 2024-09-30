package com.ottistech.indespensa.api.ms_indespensa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;

    @NotNull(message = "Field title is required")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enterprise_product_id", referencedColumnName = "enterprise_product_id")
    private EnterpriseProduct enterpriseProduct;

    @NotNull(message = "Field productPrice is required")
    @Column(name = "product_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "campaign_url", length = 255)
    private String campaignUrl;

    @NotNull(message = "Field cost is required")
    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;
}
