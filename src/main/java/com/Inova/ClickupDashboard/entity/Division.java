package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the division database table.
 * 
 */
@Entity
@NamedQuery(name="Division.findAll", query="SELECT d FROM Division d")
public class Division implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Column(name="`name`")
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datecreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	private Organization organization;

	public Division() {
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

	public void setName(String _name) {
		this.name = _name;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}