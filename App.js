import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet
} from "react-native";
import Solver from './Solver';

export default class App extends Component {

  render() {

    return (
      <View style={styles.container} >
        <Text>8 Puzzle Problem</Text>
        <Game />
      </View>
    );
  }
}

class Square extends Component {
  render() {

    return (
      <View style={styles.square}>
        <Text style={styles.no}>{this.props.value}</Text>
      </View>
    );
  }
}

class Board extends Component {

  renderSquare(i) {
    return (
      <View style={styles.square}>
        <Square value={this.props.squares[i]} />
      </View>
    );
  }

  render() {
    return (
      <View style={styles.board}>
        <View style={styles.boardRow}>
          {this.renderSquare(0)}
          {this.renderSquare(1)}
          {this.renderSquare(2)}
        </View>
        <View style={styles.boardRow}>
          {this.renderSquare(3)}
          {this.renderSquare(4)}
          {this.renderSquare(5)}
        </View>
        <View style={styles.boardRow}>
          {this.renderSquare(6)}
          {this.renderSquare(7)}
          {this.renderSquare(8)}
        </View>
      </View>
    );
  }
}

class Game extends Component {
  constructor(props) {
    super(props);
    this.state = {
      steps: [
        {
          squares: Array(9).fill(0)
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
        <View style={styles.gameBoard}>
          <Board
            squares={currentState.squares}
          />
        </View>
        <View style={styles.gameInfo}>
          <Text>Game Info Here...</Text>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 15,
  },
  game: {
    margin: 10,
    justifyContent: 'center',
  },
  gameBoard: {
    margin: 5,
  },
  gameInfo: {

  },
  board: {
    padding: 15
  },
  boardRow: {
    flexDirection: "row",
  },
  square: {
    backgroundColor: '#d1d1d1',
    width: 75,
    height: 75,
    borderWidth: 1,
    borderRadius: 10,
    alignItems: "center"
  },

});