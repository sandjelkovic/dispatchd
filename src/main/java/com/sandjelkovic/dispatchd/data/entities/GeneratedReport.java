package com.sandjelkovic.dispatchd.data.entities;

import com.sandjelkovic.dispatchd.data.EmptyCollections;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the GeneratedReport database table.
 */
@Entity
@Table(name = "GeneratedReport")
@NamedQuery(name = "GeneratedReport.findAll", query = "SELECT g FROM GeneratedReport g")
public class GeneratedReport extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = true, length = 150)
	private String text;

	//uni-directional many-to-one association to ReportTemplate
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "templateId", nullable = false)
	private ReportTemplate reportTemplate; // is it needed?

	//bi-directional many-to-one association to GeneratedReportContent
	@OneToMany(mappedBy = "generatedReport", orphanRemoval = true)
	private List<GeneratedReportContent> generatedReportContents = EmptyCollections.list();

	public GeneratedReport() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public List<GeneratedReportContent> getGeneratedReportContents() {
		return this.generatedReportContents;
	}

	public void setGeneratedReportContents(List<GeneratedReportContent> generatedReportContents) {
		this.generatedReportContents = generatedReportContents;
	}

	public GeneratedReportContent addGeneratedReportContent(GeneratedReportContent generatedReportContent) {
		getGeneratedReportContents().add(generatedReportContent);
		generatedReportContent.setGeneratedReport(this);

		return generatedReportContent;
	}

	public GeneratedReportContent removeGeneratedReportContent(GeneratedReportContent generatedReportContent) {
		getGeneratedReportContents().remove(generatedReportContent);
		generatedReportContent.setGeneratedReport(null);

		return generatedReportContent;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
