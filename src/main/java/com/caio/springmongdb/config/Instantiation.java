package com.caio.springmongdb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.caio.springmongdb.domain.Post;
import com.caio.springmongdb.domain.User;
import com.caio.springmongdb.dto.AuthorDTO;
import com.caio.springmongdb.dto.CommentDTO;
import com.caio.springmongdb.repository.PostRepository;
import com.caio.springmongdb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll( Arrays.asList(maria,alex,bob));
		
		Post post1=new Post(null, sdf.parse("21/03/2018"), "Partil Viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2=new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		CommentDTO c1=new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2=new CommentDTO("Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3=new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		
		post1.getComments().add(c1);
		post1.getComments().add(c2);
		post2.getComments().add(c3);
		
		postRepository.saveAll( Arrays.asList(post1,post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
	}

}
