package br.com.apptwitter.controller.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Message> messages;
	private List<T> datas;
	
	public Response() {
		super();
		this.setMessages(new ArrayList<Message>());
		this.setDatas(new ArrayList<T>());
	}
	
}
