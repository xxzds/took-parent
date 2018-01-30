package com.tooklili.model.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="projectname",indexStoreType="fs")
public class Post{
	@Id
	private String id;

	private String title;

	private String content;

	private int userId;

	private int weight;

	@Override
	public String toString() {
		return "Post{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + ", userId="
				+ userId + ", weight=" + weight + '}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
