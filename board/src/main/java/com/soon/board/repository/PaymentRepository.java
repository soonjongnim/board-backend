package com.soon.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soon.board.entity.PaymentsInfoEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentsInfoEntity, Long> {
	PaymentsInfoEntity getById(long paymentsNo);

}
