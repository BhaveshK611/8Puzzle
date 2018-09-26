import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
} from "react-native";

export default class App extends Component{ 

  render(){

    return(
      <View style={styles.container}>
      <Text>Yet another hello world!</Text>
      </View>     
    );
  }
}

const styles = StyleSheet.create({
 container: {
      marginTop: 5,
      alignItems: "center"
  },
  options: {
      marginTop: 15,
      flexDirection: "row",
      alignItems: "center",
      justifyContent: "space-around"
  },
});