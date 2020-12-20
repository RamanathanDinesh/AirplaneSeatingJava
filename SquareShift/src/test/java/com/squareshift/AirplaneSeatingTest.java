package com.squareshift;

import com.squareshift.utils.Position;
import com.squareshift.utils.Seating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AirplaneSeatingTest {

  AirplaneSeatingFinal airplaneSeatingFinal;

  @Before
  public void setUp() {
    // [[3,2],[4,3],[2,3],[3,4]]
    airplaneSeatingFinal = new AirplaneSeatingFinal();
  }

  /**
   * One Zone is provided as an Input, which stores 3 Rows & 2 Columns.
   */
  @Test
  public void testGetZoneDetailsFromString() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
        airplaneSeatingFinal.getZoneDetailsFromString("[[3,2]]");

    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      Assert.assertEquals(2, entry.getValue().row);
      Assert.assertEquals(3, entry.getValue().col);
      Assert.assertEquals(0, entry.getValue().zone);
      Assert.assertEquals(Position.END, entry.getValue().pos);
    }
  }

  /**If more than one zone provided,
   * Expected output:
   * First Zone must be having position as Start, Last zone position must be End and rest of the zones must be having Mid
   */
  @Test
  public void testMiddlePositionInSeatings() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3],[2,3],[3,4]]");

    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        Assert.assertEquals(2, entry.getValue().row);
        Assert.assertEquals(3, entry.getValue().col);
        Assert.assertEquals(0, entry.getValue().zone);
        Assert.assertEquals(Position.START, entry.getValue().pos);
      }
      if(entry.getKey()==3){
        Assert.assertEquals(4, entry.getValue().row);
        Assert.assertEquals(3, entry.getValue().col);
        Assert.assertEquals(3, entry.getValue().zone);
        Assert.assertEquals(Position.END, entry.getValue().pos);
      }
      if(entry.getKey()==1){
        Assert.assertEquals(3, entry.getValue().row);
        Assert.assertEquals(4, entry.getValue().col);
        Assert.assertEquals(1, entry.getValue().zone);
        Assert.assertEquals(Position.MID, entry.getValue().pos);
      }

      if(entry.getKey()==2){
        Assert.assertEquals(3, entry.getValue().row);
        Assert.assertEquals(2, entry.getValue().col);
        Assert.assertEquals(2, entry.getValue().zone);
        Assert.assertEquals(Position.MID, entry.getValue().pos);
      }
    }
  }

  /**
   * Only 2 Zones provided as an Input
   * Expected output:
   * First Zone must be having position as Start and Last zone position must be End
   */
  @Test
  public void testStartAndEndPositionInSeatings() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3]]");

    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        Assert.assertEquals(2, entry.getValue().row);
        Assert.assertEquals(3, entry.getValue().col);
        Assert.assertEquals(0, entry.getValue().zone);
        Assert.assertEquals(Position.START, entry.getValue().pos);
      }
      if(entry.getKey()==1){
        Assert.assertEquals(3, entry.getValue().row);
        Assert.assertEquals(4, entry.getValue().col);
        Assert.assertEquals(1, entry.getValue().zone);
        Assert.assertEquals(Position.END, entry.getValue().pos);
      }
    }
  }

  /**
   * Aisle position is must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone,
   * total number of Aisle count and aisle column details must be validated.
   * In Start and End zones, only 1 aisle must be there
   */
  @Test
  public void testAislePositionInSeatingLayout() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3]]");

    List<Integer> expectedAislePosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedAislePosition = new ArrayList<>();
        expectedAislePosition.add(2);
        Assert.assertEquals(1, entry.getValue().aisle.size());
        Assert.assertEquals(expectedAislePosition, entry.getValue().aisle);
      }
      if(entry.getKey()==1){
        expectedAislePosition = new ArrayList<>();
        expectedAislePosition.add(0);
        Assert.assertEquals(1, entry.getValue().aisle.size());
        Assert.assertEquals(expectedAislePosition, entry.getValue().aisle);
      }
    }
  }

  /**
   * Aisle position is must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone,
   * total number of Aisle count and aisle column details must be validated.
   * In Start and End zones, only 1 aisle must be there and in the middle zones there must be 2 aisle seats
   */
  @Test
  public void testAislePositionInSeatingLayout2() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3],[4,3]]");

    List<Integer> expectedAislePosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedAislePosition = new ArrayList<>();
        expectedAislePosition.add(2);
        Assert.assertEquals(1, entry.getValue().aisle.size());
        Assert.assertEquals(expectedAislePosition, entry.getValue().aisle);
      }
      if(entry.getKey()==1){
        expectedAislePosition = new ArrayList<>();
        expectedAislePosition.add(0);
        expectedAislePosition.add(3);
        Assert.assertEquals(2, entry.getValue().aisle.size());
        Assert.assertEquals(expectedAislePosition, entry.getValue().aisle);
      }
      if(entry.getKey()==2){
        expectedAislePosition = new ArrayList<>();
        expectedAislePosition.add(0);
        Assert.assertEquals(1, entry.getValue().aisle.size());
        Assert.assertEquals(expectedAislePosition, entry.getValue().aisle);
      }
    }
  }

  /**
   * Window position is must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone total number of Window count and column details must
   * be validated. In Start and End zones, only 1 window must be there
   * and in the middle zones there shouldn't be any window seats
   */
  @Test
  public void testWindowPositionInSeatingLayout() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[3,4]]");

    List<Integer> expectedWindowPosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedWindowPosition = new ArrayList<>();
        expectedWindowPosition.add(0);
        Assert.assertEquals(1, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
      if(entry.getKey()==1){
        expectedWindowPosition = new ArrayList<>();
        expectedWindowPosition.add(2);
        Assert.assertEquals(1, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
    }
  }

  /**
   * Window position is must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone total number of Window count and column details must
   * be validated. In Start and End zones, only 1 window must be there
   * and in the middle zones there shouldn't be any window seats
   */
  @Test
  public void testWindowPositionInSeatingLayout1() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3],[2,3],[3,4]]");

    List<Integer> expectedWindowPosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedWindowPosition = new ArrayList<>();
        expectedWindowPosition.add(0);
        Assert.assertEquals(1, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
      if(entry.getKey()==1){
        expectedWindowPosition = new ArrayList<>();
        Assert.assertEquals(0, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
      if(entry.getKey()==2){
        expectedWindowPosition = new ArrayList<>();
        Assert.assertEquals(0, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
      if(entry.getKey()==3){
        expectedWindowPosition = new ArrayList<>();
        expectedWindowPosition.add(2);
        Assert.assertEquals(1, entry.getValue().window.size());
        Assert.assertEquals(expectedWindowPosition, entry.getValue().window);
      }
    }
  }

  /**
   * Middle/Center position must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone total number of Middle count and column details must
   * be validated.
   * Other than Window and Aisle seats, if there is any seats to be considered as Middle or Center seats
   */
  @Test
  public void testMiddlePositionInSeatingLayout() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[3,2],[4,3],[2,3],[3,4]]");

    List<Integer> expectedMiddlePosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedMiddlePosition = new ArrayList<>();
        expectedMiddlePosition.add(1);
        Assert.assertEquals(1, entry.getValue().middle.size());
        Assert.assertEquals(expectedMiddlePosition, entry.getValue().middle);
      }
      if(entry.getKey()==1){
        expectedMiddlePosition = new ArrayList<>();
        expectedMiddlePosition.add(1);
        expectedMiddlePosition.add(2);
        Assert.assertEquals(2, entry.getValue().middle.size());
        Assert.assertEquals(expectedMiddlePosition, entry.getValue().middle);
      }

      if(entry.getKey()==3){
        expectedMiddlePosition = new ArrayList<>();
        expectedMiddlePosition.add(1);
        Assert.assertEquals(1, entry.getValue().middle.size());
        Assert.assertEquals(expectedMiddlePosition, entry.getValue().middle);
      }
    }
  }

  /**
   * Middle/Center position must be obtained from the given seating layout.
   * Expected Output: In the given seat layout with respect to Zone total number of Middle count and column details must
   * be validated.
   * Other than Window and Aisle seats, if there is any seats to be considered as Middle or Center seats
   */
  @Test
  public void testMiddlePositionInSeatingLayout1() {
    LinkedHashMap<Integer, Seating> ActualSeatings =
            airplaneSeatingFinal.getZoneDetailsFromString("[[2,2],[2,3]]");

    List<Integer> expectedMiddlePosition = new ArrayList<>();
    for(Map.Entry<Integer, Seating> entry: ActualSeatings.entrySet()){
      if(entry.getKey()==0){
        expectedMiddlePosition = new ArrayList<>();
        Assert.assertEquals(0, entry.getValue().middle.size());
        Assert.assertEquals(expectedMiddlePosition, entry.getValue().middle);
      }
      if(entry.getKey()==1){
        Assert.assertEquals(0, entry.getValue().middle.size());
      }

      if(entry.getKey()==3){
        expectedMiddlePosition = new ArrayList<>();
        expectedMiddlePosition.add(1);
        Assert.assertEquals(1, entry.getValue().middle.size());
        Assert.assertEquals(expectedMiddlePosition, entry.getValue().middle);
      }
    }
  }
}
