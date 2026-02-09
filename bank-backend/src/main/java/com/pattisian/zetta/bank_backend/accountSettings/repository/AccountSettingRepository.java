package com.pattisian.zetta.bank_backend.accountSettings.repository;

import com.pattisian.zetta.bank_backend.accountSettings.entity.AccountSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSettingRepository extends JpaRepository<AccountSetting, Long> {
}
