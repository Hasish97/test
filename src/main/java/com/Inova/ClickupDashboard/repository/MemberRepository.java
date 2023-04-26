package com.Inova.ClickupDashboard.repository;


import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Integer>{
    Member getMemberById(Integer id);

}
