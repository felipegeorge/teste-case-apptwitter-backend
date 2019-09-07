package br.com.apptwitter.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_tweet")
@NamedQuery(name = "TweetEntity.findAll", query = "SELECT t FROM TweetEntity t")
public class TweetEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	private Long id;

	@NonNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@NonNull
	@Lob
	@Column(name = "text", columnDefinition = "CLOB")
	private String text;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "tb_user_id")
	private UserEntity user;

	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
	private List<TagEntity> tagList;
	
//	@Override
//	public String toString() {
//		ObjectMapper mapper = new ObjectMapper();
//
//		String jsonStr = "";
//		try {
//			mapper.enable(SerializationFeature.INDENT_OUTPUT);
//			jsonStr = mapper.writeValueAsString( new TweetEntity(this.getId(), this.getCreatedAt(), this.getText(), null, this.getTagList()) );
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonStr;
//	}
}
