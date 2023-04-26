package com.Inova.ClickupDashboard.repository;

import com.Inova.ClickupDashboard.entity.Member;
import com.Inova.ClickupDashboard.entity.ProjectFolder;
import com.Inova.ClickupDashboard.entity.ProjectFolderSharedmember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectFolderSharedMemberRepository extends JpaRepository<ProjectFolderSharedmember,Integer>{
    ProjectFolderSharedmember getProjectFolderSharedmemberByProjectFolderAndMember(ProjectFolder projectfolder, Member member);
  }
