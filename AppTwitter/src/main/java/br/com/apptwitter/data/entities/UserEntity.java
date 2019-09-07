package br.com.apptwitter.data.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_user")
@NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u")
public class UserEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	private Long id;
	@NonNull
	private String userName;
	@NonNull
	private Integer followersCount;
	private String language;
	private String location;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<TweetEntity> tweetList;
	
//	@Override
//	public String toString() {
//		ObjectMapper mapper = new ObjectMapper();
//
//		String jsonStr = "";
//		try {
//			mapper.enable(SerializationFeature.INDENT_OUTPUT);
//			jsonStr = mapper.writeValueAsString( new UserEntity(this.getId(), this.getUserName(), this.getFollowersCount(), null, this.getLocation(), null ) );
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonStr;
//	}
}
