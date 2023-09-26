package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rate")
public class RateEntity extends BaseEntity{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column(name = "rate")
   private Integer rate;
   @Column(name = "comment")
   private String comment;
   @Column(name = "color")
   private Boolean color;
   @Column(name = "wrong_product")
   private Boolean wrongProduct;
   @Column(name = "lack_of_accessories")
   private Boolean lackOfAccessories;
   @Column(name = "material")
   private Boolean material;
   @Column(name = "detailBill_id")
   private Integer detailBillID;
   @Column(name = "productID")
   private Integer productID;
   @Column(name = "userName")
   private String  userName;
   @Column(name = "deleted")
   private Boolean deleted;
   @Column(name="right_to_edit")
   private Boolean rightToEdit;


}
