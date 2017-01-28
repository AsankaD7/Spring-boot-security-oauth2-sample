package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>{
	Optional<Account> findByUsername(String username);
}
