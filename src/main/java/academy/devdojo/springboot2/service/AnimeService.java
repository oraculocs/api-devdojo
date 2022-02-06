package academy.devdojo.springboot2.service;

import java.util.List;

import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
@RequiredArgsConstructor
public class AnimeService {

	private final AnimeRepository animeRepository;
	
	public List<Anime> listAll(){
		return animeRepository.findAll();
	}

	public Anime findByIdOrThrownBadRequestException(Long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}

	public Anime save(AnimePostRequestBody animePostRequestBody) {

		return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
	}

	public void delete(Long id) {

		animeRepository.delete(findByIdOrThrownBadRequestException(id)); ;
	}

	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime savedAnime = findByIdOrThrownBadRequestException(animePutRequestBody.getId());
		Anime anime = Anime.builder()
				.id(savedAnime.getId())
				.name(animePutRequestBody.getName())
				.build();


		animeRepository.save(anime);


	}
}
