package com.eureka.note.entities;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Document
public class Note {
   
//	@Id
	private String id;
	private String userId;
	private String noteHeader;
	private String noteDetail;
	
	public Note(String id, String userId, String noteHeader, String noteDetail ) {
		this.setId(id);
		this.setUserId(userId);
		this.noteHeader = noteHeader;
		this.noteDetail = noteDetail;
 
	}
	public Note( String userId, String noteHeader, String noteDetail ) {
		 
		this.setUserId(userId);
		this.noteHeader = noteHeader;
		this.noteDetail = noteDetail;
 
	}
 
 

	public Note() {
		// TODO Auto-generated constructor stub
	}
	public String getNoteDetail() {
		return noteDetail;
	}

	public void setNoteDetail(String noteDetail) {
		this.noteDetail = noteDetail;
	}
 

	public String getNoteHeader() {
		return noteHeader;
	}

	public void setNoteHeader(String noteHeader) {
		this.noteHeader = noteHeader;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
 
}

