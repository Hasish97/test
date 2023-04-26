package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the `project_ role` database table.
 * 
 */
@Entity
@Table(name="`project_ role`")
@NamedQuery(name="Project_role.findAll", query="SELECT p FROM Project_role p")
public class Project_role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	private String name;

	//bi-directional many-to-one association to ProjectRole_member
	@OneToMany(mappedBy="projectRole")
	private List<ProjectRole_member> projectRoleMembers;

	public Project_role() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProjectRole_member> getProjectRoleMembers() {
		return this.projectRoleMembers;
	}

	public void setProjectRoleMembers(List<ProjectRole_member> projectRoleMembers) {
		this.projectRoleMembers = projectRoleMembers;
	}

	public ProjectRole_member addProjectRoleMember(ProjectRole_member projectRoleMember) {
		getProjectRoleMembers().add(projectRoleMember);
		projectRoleMember.setProjectRole(this);

		return projectRoleMember;
	}

	public ProjectRole_member removeProjectRoleMember(ProjectRole_member projectRoleMember) {
		getProjectRoleMembers().remove(projectRoleMember);
		projectRoleMember.setProjectRole(null);

		return projectRoleMember;
	}

}