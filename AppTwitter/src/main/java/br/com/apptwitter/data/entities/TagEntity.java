package br.com.apptwitter.data.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "tb_hashtag")
@NamedQuery(name = "TagEntity.findAll", query = "SELECT h FROM TagEntity h")
public class TagEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIBERNATE_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "HIBERNATE_SEQUENCE_GENERATOR", sequenceName = "HIBERNATE_SEQUENCE", allocationSize = 1)
	@NonNull
	private Long id;
	
	@NonNull
	private String tag;
	
	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "tb_tweet_id")
    private TweetEntity tweet;

//	@Override
//	public String toString() {
//		ObjectMapper mapper = new ObjectMapper();
//
//		String jsonStr = "";
//		try {
//			mapper.enable(SerializationFeature.INDENT_OUTPUT);
//			jsonStr = mapper.writeValueAsString( new TagEntity(this.getId(), this.getTag()) );
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonStr;
//	}
}
