package com.qlmh.datn_qlmh.configs;

import com.qlmh.datn_qlmh.configs.mail.MailInfor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrapMailBillAndThymeleaf {
    MailInfor mailInfor;
    BillForThymeleaf register;
}
