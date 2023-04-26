package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the organization database table.
 * 
 */
@Entity
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Division
	@OneToMany(mappedBy="organization")
	private List<Division> divisions;

	//bi-directional many-to-one association to Member
	@OneToMany(mappedBy="organization")
	private List<Member> members;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="organization")
	private List<Project> projects;

	public Organization() {
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

	public List<Division> getDivisions() {
		return this.divisions;
	}

	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}

	public Division addDivision(Division division) {
		getDivisions().add(division);
		division.setOrganization(this);

		return division;
	}

	public Division removeDivision(Division division) {
		getDivisions().remove(division);
		division.setOrganization(null);

		return division;
	}

	public List<Member> getMembers() {
		return this.members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Member addMember(Member member) {
		getMembers().add(member);
		member.setOrganization(this);

		return member;
	}

	public Member removeMember(Member member) {
		getMembers().remove(member);
		member.setOrganization(null);

		return member;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setOrganization(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setOrganization(null);

		return project;
	}

}