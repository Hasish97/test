package com.Inova.ClickupDashboard.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mst_project_details database table.
 * 
 */
@Entity
@Table(name="mst_project_details")
@NamedQuery(name="MstProjectDetail.findAll", query="SELECT m FROM MstProjectDetail m")
public class MstProjectDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="mst_quatationvalue_actual_shared")
	private float mstQuatationvalueActualShared;

	@Column(name="mst_quatationvalue_calulated")
	private float mstQuatationvalueCalulated;

	@Column(name="mst_quoted_effort")
	private float mstQuotedEffort;

	@Column(name="mst_quoted_man_day_rate")
	private float mstQuotedManDayRate;

	@Column(name="mst_total_effort_spent")
	private float mstTotalEffortSpent;

	@Column(name="mst_standard_man_day_rate")
	private float mstStandardManDayRate;

	//bi-directional many-to-one association to Project
	@ManyToOne
	private Project project;

	public MstProjectDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public float getMstQuatationvalueActualShared() {
		return this.mstQuatationvalueActualShared;
	}

	public void setMstQuatationvalueActualShared(float mstQuatationvalueActualShared) {
		this.mstQuatationvalueActualShared = mstQuatationvalueActualShared;
	}

	public float getMstQuatationvalueCalulated() {
		return this.mstQuatationvalueCalulated;
	}

	public void setMstQuatationvalueCalulated(float mstQuatationvalueCalulated) {
		this.mstQuatationvalueCalulated = mstQuatationvalueCalulated;
	}

	public float getMstQuotedEffort() {
		return this.mstQuotedEffort;
	}

	public void setMstQuotedEffort(float mstQuotedEffort) {
		this.mstQuotedEffort = mstQuotedEffort;
	}

	public float getMstQuotedManDayRate() {
		return this.mstQuotedManDayRate;
	}

	public void setMstQuotedManDayRate(float mstQuotedManDayRate) {
		this.mstQuotedManDayRate = mstQuotedManDayRate;
	}

	public float getMstTotalEffortSpent() {
		return this.mstTotalEffortSpent;
	}

	public void setMstTotalEffortSpent(float mstTotalEffortSpent) {
		this.mstTotalEffortSpent = mstTotalEffortSpent;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public float getmstStandardManDayRate() {
		return mstStandardManDayRate;
	}

	public void setmstStandardManDayRate(float mstStandardManDayRate) {
		this.mstStandardManDayRate = mstStandardManDayRate;
	}
}