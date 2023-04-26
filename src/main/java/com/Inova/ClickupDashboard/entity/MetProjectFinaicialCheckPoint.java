package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the met_project_finaicial_check_point database table.
 * 
 */
@Entity
@Table(name="met_project_finaicial_check_point")
@NamedQuery(name="MetProjectFinaicialCheckPoint.findAll", query="SELECT m FROM MetProjectFinaicialCheckPoint m")
public class MetProjectFinaicialCheckPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Column(name="`met_percentage`")
	private float metPercentage;

	@Column(name="met_derive_percentage")
	private float metDerivePercentage;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	public MetProjectFinaicialCheckPoint() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int int_) {
		this.id = int_;
	}

	public float getMetPercentage() {
		return this.metPercentage;
	}

	public void setMetPercentage(float _metPercentage) {
		this.metPercentage = _metPercentage;
	}

	public float getMetDerivePercentage() {
		return this.metDerivePercentage;
	}

	public void setMetDerivePercentage(float metDerivePercentage) {
		this.metDerivePercentage = metDerivePercentage;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}