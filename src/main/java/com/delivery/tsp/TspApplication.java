package com.delivery.tsp;

import com.delivery.tsp.controller.DeliveryPointController;
import com.delivery.tsp.dto.CourierDto;
import com.delivery.tsp.dto.CourierJson;
import com.delivery.tsp.service.DeliveryPointService;
import com.delivery.tsp.utils.HamiltonCycle;
import com.delivery.tsp.utils.HamiltonianCycle;
import com.delivery.tsp.utils.brain.Vertex;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.delivery.tsp.utils.brain.Vertex.*;

@SpringBootApplication
public class TspApplication implements CommandLineRunner {

	private DeliveryPointController deliveryPointController;

	public TspApplication(DeliveryPointController deliveryPointController) {
		this.deliveryPointController = deliveryPointController;
	}

	private static Logger log = LoggerFactory
			.getLogger(TspApplication.class);

	public static void main(String[] args) {
		log.info(">>>start main ....");
		SpringApplication.run(TspApplication.class, args);
		log.info(">>>finish main ...");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(">>>command line runner ...");

		//read from json file
		ObjectMapper mapper = new ObjectMapper();
		//TypeReference<List<CourierDto>> typeReference = new TypeReference<List<CourierDto>>(){};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/json/courier.json");
		//List<CourierDto> courier=new ArrayList<>();
		CourierJson courierJson=new CourierJson();
		CourierDto courierDto=null;
		try {
			//courier = mapper.readValue(inputStream,typeReference);
			courierJson = mapper.readValue(inputStream,CourierJson.class);

			validateCourierSpeedJson(courierJson);

			//userService.save(users);
			System.out.println("from courier.json: " + courierJson);
			courierDto=convertCourierJsonToCourierDto(courierJson);
			System.out.println("courierDto: " +courierJson);
			//System.out.println("Users Saved!");
		} catch (IOException e){
			System.out.println("Unable courier: " + e.getMessage());
		}

		String rout = deliveryPointController.findRout(courierDto);
		System.out.println( rout);


//		CourierDto courierDto=new CourierDto();
//		courierDto.setBeginWorkDay(LocalTime.of(14,0,0));
//		courierDto.setEndWorkDay(LocalTime.of(23,0,0));
//		courierDto.setSpendTime(new int[]{20, 30, 15, 25, 10});
//		courierDto.setSpeed(60);
//		courierDto.setLotitude(50.4085203);
//		courierDto.setLongitude(30.5743270);



	}

	private static void validateCourierSpeedJson(CourierJson courierJson){
		int courierSpeed=courierJson.getSpeed();

		if(courierSpeed<=0){
			throw new RuntimeException("Courier speed must be >0");
		}
	}

//	private static void validateCourierTimeJson(CourierJson courierJson){
//		courierJson.
//		courierJson.getBeginWorkDay()
//	}

	private static CourierDto convertCourierJsonToCourierDto(CourierJson courierJson){

		CourierDto courierDto=new CourierDto();
		courierDto.setBeginWorkDay(LocalTime.of(
				getSplitTime(courierJson.getBeginWorkDay(),TimeUnit.HOURS)
				,getSplitTime(courierJson.getBeginWorkDay(),TimeUnit.MINUTES)
				,getSplitTime(courierJson.getBeginWorkDay(),TimeUnit.SECONDS)));

		courierDto.setEndWorkDay(LocalTime.of(
				getSplitTime(courierJson.getEndWorkDay(),TimeUnit.HOURS)
				,getSplitTime(courierJson.getEndWorkDay(),TimeUnit.MINUTES)
				,getSplitTime(courierJson.getEndWorkDay(),TimeUnit.SECONDS)));

		courierDto.setSpendTime(courierJson.getSpendTime());
		courierDto.setSpeed(courierJson.getSpeed());
		courierDto.setLotitude(courierJson.getLotitude());
		courierDto.setLongitude(courierJson.getLongitude());

		return courierDto;
	}

	private static int getSplitTime(String strDate,TimeUnit timeUnit ){

		String[] split = strDate.split(":");

		int[] time=new int[3];
		int hour=Integer.valueOf(split[0]);
		int minute=Integer.valueOf(split[1]);
		int second=Integer.valueOf(split[2]);


		if(timeUnit==TimeUnit.HOURS)
			return hour;
		else if(timeUnit==TimeUnit.MINUTES)
			return minute;
		else if (timeUnit==TimeUnit.SECONDS)
			return second;
		else
			throw new IllegalArgumentException("Wronge time part");


	}

}
