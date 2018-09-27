import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  Button
} from "react-native";
import Solver from './Solver';

export default class App extends Component {

  constructor(props) {
    super(props);
    this.setInitialConf = this.setInitialConf.bind(this);
    this.setFinalConf = this.setFinalConf.bind(this);
    this.state = {
      initialConf: [3, 0, 7, 2, 8, 1, 6, 4, 5],
      finalConf: [1, 2, 3, 4, 5, 6, 7, 8, 0],
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
      <View style={styles.container} >
        <Text>8 Puzzle Problem</Text>
        <Input initialConf={this.state.initialConf} finalConf={this.state.finalConf} setInitialConf={this.setInitialConf} setFinalConf={this.setFinalConf} />
        <Button
          onPress={() => {
            console.log(this.state);
            Solver.solve(this.state.initialConf.join('#'), this.state.finalConf.join('#'), (path, exploredCount) => {
              console.log("Solution: " + path + " " + path.length + " " + exploredCount);
            });
          }}
          title="Solve"
          color="#2e6060"
        />
        <Game />
      </View>
    );
  }
}

/**
 * Input Class: To take initial and final input from user
 */

class Input extends Component {

  render() {

    return (
      <View style={styles.input}>
        <InputBoard squares={this.props.initialConf} setConf={this.props.setInitialConf} />
        <InputBoard squares={this.props.finalConf} setConf={this.props.setFinalConf} />
      </View>
    );
  }
}


/**
* Input Board
*/

class InputBoard extends Component {

  renderInputSquare(i) {
    return (
      <InputSquare index={i} value={this.props.squares[i]} setConf={this.props.setConf} />
    );
  }

  render() {
    return (
      <View style={styles.inputBoard}>
        <View style={styles.inputBoardRow}>
          {this.renderInputSquare(0)}
          {this.renderInputSquare(1)}
          {this.renderInputSquare(2)}
        </View>
        <View style={styles.inputBoardRow}>
          {this.renderInputSquare(3)}
          {this.renderInputSquare(4)}
          {this.renderInputSquare(5)}
        </View>
        <View style={styles.inputBoardRow}>
          {this.renderInputSquare(6)}
          {this.renderInputSquare(7)}
          {this.renderInputSquare(8)}
        </View>
      </View>
    );
  }
}

/**
* Input Sqaure
*/

class InputSquare extends Component {

  constructor(props) {
    super(props);
    this.state = { value: String(this.props.value) };
  }

  render() {

    return (
      <View style={styles.inputSquare}>
        <TextInput
          style={{}}
          onChangeText={(value) => {
            this.setState({ value: value });
            this.props.setConf(this.props.index, value);
          }}
          value={this.state.value}
          keyboardType='phone-pad'
        />
      </View>
    );
  }
}


/**
 * Game Class: To show some moves! ;)
 */


class Game extends Component {
  constructor(props) {
    super(props);
    this.state = {
      steps: [
        {
          squares: Array(9).fill('')
        }
      ],
      stepNumber: 0,
    };
  }

  render() {

    const steps = this.state.steps;
    var currentState = steps[this.state.stepNumber];

    return (
      <View style={styles.game}>
        <GameBoard squares={currentState.squares} />
        <View style={styles.gameInfo}>
          <Text>Game Info Here...</Text>
        </View>
      </View>
    );
  }
}

/**
 * Game Board
 */

class GameBoard extends Component {

  renderGameSquare(i) {
    return (
      <GameSquare value={this.props.squares[i]} />
    );
  }

  render() {
    return (
      <View style={styles.gameBoard}>
        <View style={styles.gameBoardRow}>
          {this.renderGameSquare(0)}
          {this.renderGameSquare(1)}
          {this.renderGameSquare(2)}
        </View>
        <View style={styles.gameBoardRow}>
          {this.renderGameSquare(3)}
          {this.renderGameSquare(4)}
          {this.renderGameSquare(5)}
        </View>
        <View style={styles.gameBoardRow}>
          {this.renderGameSquare(6)}
          {this.renderGameSquare(7)}
          {this.renderGameSquare(8)}
        </View>
      </View>
    );
  }
}

/**
 * Game Square
 */

class GameSquare extends Component {
  render() {

    return (
      <View style={styles.gameSquare}>
        <Text style={styles.no}>{this.props.value}</Text>
      </View>
    );
  }
}

/**
 * Styles
 */

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 15,
    alignItems: 'center',
    //backgroundColor: '#f9fffb'
  },

  input: {
    margin: 10,
    justifyContent: 'center',
    flexDirection: "row",
  },
  inputBoard: {
    margin: 5,
    padding: 15
  },
  inputBoardRow: {
    flexDirection: "row",
  },
  inputSquare: {
    backgroundColor: '#88cea5',
    width: 45,
    height: 45,
    borderWidth: 1,
    borderRadius: 10,
    borderColor: '#2f6843',
    alignItems: "center"
  },

  game: {
    margin: 10,
    justifyContent: 'center',
  },
  gameInfo: {

  },
  gameBoard: {
    margin: 5,
    padding: 15
  },
  gameBoardRow: {
    flexDirection: "row",
  },
  gameSquare: {
    backgroundColor: '#88cea5',
    width: 75,
    height: 75,
    borderWidth: 1,
    borderRadius: 10,
    borderColor: '#2f6843',
    alignItems: "center"
  }

});