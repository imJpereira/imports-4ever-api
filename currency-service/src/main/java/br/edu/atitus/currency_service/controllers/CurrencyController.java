package br.edu.atitus.currency_service.controllers;

import br.edu.atitus.currency_service.clients.CurrencyBCClient;
import br.edu.atitus.currency_service.clients.CurrencyBCReponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.currency_service.entities.CurrencyEntity;
import br.edu.atitus.currency_service.repositories.CurrencyRepository;

import java.util.Locale;

@RestController
@RequestMapping("currency")
public class CurrencyController {
	
	private final CurrencyRepository repository;
	private final CurrencyBCClient currencyBCClient;
	private final CacheManager cacheManager;

	public CurrencyController(CurrencyRepository repository, CurrencyBCClient currencyBCClient, CacheManager cacheManager) {
		this.repository = repository;
		this.currencyBCClient = currencyBCClient;
		this.cacheManager = cacheManager;
	}
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/{value}/{source}/{target}")
	public ResponseEntity<CurrencyEntity> getCurrency(
			@PathVariable double value,
			@PathVariable String source,
			@PathVariable String target
			) throws Exception {


		source = source.toUpperCase();
		target = target.toUpperCase();
		String dataSource = "None";
		String nameCache = "Currency";
		String keyCache = source + target;

		CurrencyEntity currencyEntity = cacheManager.getCache(nameCache).get(keyCache, CurrencyEntity.class);

		if (currencyEntity != null) {
		 	dataSource = "cache";
		} else {
			currencyEntity = new CurrencyEntity();
			currencyEntity.setSource(source);
			currencyEntity.setTarget(target);

			if (source.equals(target)) {
				currencyEntity.setConversionRate(1);
			} else {
				try {
					double currencySource = 1;
					double currencyTarget = 1;

					if (!source.equals("BRL")) {
						CurrencyBCReponse response = currencyBCClient.getCurrencyBC(source);
						if (response.getValue().isEmpty()) throw new Exception("currency not found for " + source);
						currencySource = response.getValue().get(0).getCotacaoVenda();
					}

					if (!target.equals("BRL")) {
						CurrencyBCReponse response = currencyBCClient.getCurrencyBC(target);
						if (response.getValue().isEmpty()) throw new Exception("currency not found for " + target);
						currencyTarget = response.getValue().get(0).getCotacaoVenda();
					}

					currencyEntity.setConversionRate(currencySource / currencyTarget);
					dataSource = "API BCB";
				} catch (Exception e) {
					currencyEntity = repository.findBySourceAndTarget(source, target)
							.orElseThrow(() -> new Exception("Currency unsupported"));
					dataSource = "Local Database";
				}

			}

			cacheManager.getCache(nameCache).put(keyCache, currencyEntity);
		}
		currencyEntity.setConvertedValue(value * currencyEntity.getConversionRate());
		currencyEntity.setEnvironment("service: currency - port: "+serverPort + " - source: "+dataSource);

		return ResponseEntity.ok(currencyEntity);
	}
	

}
