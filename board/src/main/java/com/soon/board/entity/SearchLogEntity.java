package com.soon.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="search_log")
@Table(name="search_log")
public class SearchLogEntity {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sequence;
	private String searchWord;
	private String relationWord;
	private boolean relation;
}
