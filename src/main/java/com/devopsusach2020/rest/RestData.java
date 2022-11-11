@GetMapping(path = "/estadoPais", produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody Pais  getTotalPais(@RequestParam(name = "pais") String message) {

        ResponseEntity <String> call = restTemplate.getForEntity("https://api.covid19api.com/live/country/" + message, String.class);

        LOGGER.log(Level.INFO, "Consulta por pais");

          Pais response = new Pais();
          int confirmed = 0;
          int death = 0;
          int recovered = 0;
          Gson gson = new Gson();

        Pais[] estados = gson.fromJson(call.getBody().toLowerCase(), Pais[].class);

        for (Pais estado: estados) {
            response.setDate(estado.getDate());
            response.setActive(estado.getActive());
            confirmed += estado.getConfirmed();
            death += estado.getDeaths();
            recovered += estado.getRecovered();
        }

        response.setConfirmed(confirmed);
        response.setDeaths(death);
        response.setRecovered(recovered);
        response.setCountry(message);
        response.setMensaje("ok");
        
        return response;
      }