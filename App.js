import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Button
} from "react-native";
import Solver from './src/Solver';
import Input from './src/Input';
import Game from './src/Game';

export default class App extends Component {

  constructor(props) {
    super(props);
    this.setInitialConf = this.setInitialConf.bind(this);
    this.setFinalConf = this.setFinalConf.bind(this);
    this.state = {
      // initialConf: [0,1,2,3,4,5,6,7,8],
      // finalConf: [1,0,2, 3, 4, 5, 6, 7, 8],
      initialConf: [3, 0, 7, 2, 8, 1, 6, 4, 5],
      finalConf: [1, 2, 3, 4, 5, 6, 7, 8, 0],
      gameComp: null,
    }
  }

  setInitialConf(i, val) {
    let initialConf = [...this.state.initialConf];
    initialConf[i] = val;
    this.setState({ initialConf: initialConf });
  }

  setFinalConf(i, val) {
    let finalConf = [...this.state.finalConf];
    finalConf[i] = val;
    this.setState({ finalConf: finalConf });
  }

  render() {

    return (
      <View style={styles.container}>
        <Text style={styles.titleText}>8 Puzzle Solver</Text>
        <Input initialConf={this.state.initialConf} finalConf={this.state.finalConf} setInitialConf={this.setInitialConf} setFinalConf={this.setFinalConf} />
        <Button
          onPress={() => {
            this.setState({ gameComp: null });
            Solver.solve(this.state.initialConf.join(','), this.state.finalConf.join(','), 'l', (errorCode, path, exploredCount) => {
              console.log("[" + errorCode + "]: " + "Solution: path: " + path + " path-length: " + path.length + " " + exploredCount);
              this.setState({
                gameComp: (<Game solution={{
                  errorCode: errorCode,
                  path: path,
                  exploredCount: exploredCount,
                }} initialConf={this.state.initialConf} />)
              });
            });
          }}
          title="Solve"
          color="#2e6060"
        />
        {this.state.gameComp}
      </View >
    );
  }
}


/**
 * Styles
 */
const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 10,
    alignItems: 'center',
    backgroundColor: '#edf9f1',
  },
  titleText: {
    fontSize: 20,
    color: '#1a4c63',
    fontWeight: 'bold',
    fontFamily: 'Baskerville',
    borderBottomWidth: 2,
    borderColor: '#1a4c63',
    borderRadius: 10,

  },
});