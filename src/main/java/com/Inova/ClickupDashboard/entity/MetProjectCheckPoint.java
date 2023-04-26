package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the met_project_check_point database table.
 * 
 */
@Entity
@Table(name="met_project_check_point")
@NamedQuery(name="MetProjectCheckPoint.findAll", query="SELECT m FROM MetProjectCheckPoint m")
public class MetProjectCheckPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Column(name="met_derive_percentage")
	private float metDerivePercentage;

	@Column(name="met_percentage")
	private float metPercentage;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	public MetProjectCheckPoint() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getMetDerivePercentage() {
		return this.metDerivePercentage;
	}

	public void setMetDerivePercentage(float metDerivePercentage) {
		this.metDerivePercentage = metDerivePercentage;
	}

	public float getMetPercentage() {
		return this.metPercentage;
	}

	public void setMetPercentage(float metPercentage) {
		this.metPercentage = metPercentage;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}