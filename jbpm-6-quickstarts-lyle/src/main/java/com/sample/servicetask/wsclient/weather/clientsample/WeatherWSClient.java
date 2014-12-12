package com.sample.servicetask.wsclient.weather.clientsample;

import javax.xml.bind.JAXBException;

import com.sample.servicetask.wsclient.weather.GlobalWeather;
import com.sample.servicetask.wsclient.weather.GlobalWeatherSoap;

public class WeatherWSClient {

    public static void callWeatherWS() throws JAXBException {

        GlobalWeather service = new GlobalWeather();
        GlobalWeatherSoap port = service.getGlobalWeatherSoap12();

        String returnedWeather = port.getWeather("brisbane", "australia");
        System.out.println("Current Weather:");
        System.out.println(returnedWeather);
    }
    
    public static void main(String[] args) throws JAXBException {

        callWeatherWS();
    }
}
