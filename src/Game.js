import React, { Component } from "react";
import {
    View,
    Text,
    StyleSheet,
} from "react-native";

/**
 * Game Class: To show some moves! ;)
 */

export default class Game extends Component {

    constructor(props) {
        super(props);
        this.state = {
            squares: Array(9).fill(0),
            intervalId: null,
            tilePos: -1,
            step: 0,
            direction: '',
            errorString: "",
        };
        this.solvePuzzle = this.solvePuzzle.bind(this);
    }

    componentDidMount() {

        if (this.props.solution.errorCode != 0) {
            let errorString = "";
            switch (this.props.solution.errorCode) {
                case -1:
                    errorString = "Not possible to reach goal state from given initial state!";
                    break;

                case -2:
                    errorString = "Error in Initial State:\n\nPlease fill up all tiles.";
                    break;

                case -3:
                    errorString = "Error in Goal State:\n\nPlease fill up all tiles.";
                    break;

                case -4:
                    errorString = "Invalid Initial State:\n\nOnly numbers allowed";
                    break;

                case -5:
                    errorString = "Invalid Goal State:\n\nOnly numbers allowed";
                    break;

                case -6:
                    errorString = "Invalid Initial State:\n\nPlease enter each number only once";
                    break;

                case -7:
                    errorString = "Invalid Goal State:\n\nPlease enter each number only once";
                    break;

                case -8:
                    errorString = "Invalid Initial State:\n\nOnly numbers between 0-8 allowed";
                    break;

                case -9:
                    errorString = "Invalid Goal State:\n\nOnly numbers between 0-8 allowed";
                    break;

                default:
                    break;
            }
            this.setState({
                errorString: errorString
            });
        }
        else {

            for (var i = 0; i < 9; i++) {
                if (this.props.initialConf[i] == 0) {
                    this.setState({ tilePos: i });
                    break;
                }
            }

            this.setState({
                squares: [...this.props.initialConf],
                direction: '',
                step: 0,
            });

            var intervalId = setInterval(this.solvePuzzle, 500);
            this.setState({ intervalId: intervalId });
        }
    }

    componentWillUnmount() {
        clearInterval(this.state.intervalId);
    }

    solvePuzzle() {

        if (this.state.step < this.props.solution.path.length) {
            var squares = this.state.squares;
            var x = this.state.tilePos, y = x, dir = '';
            switch (this.props.solution.path.charAt(this.state.step)) {
                case 'U':
                    y = x - 3;
                    dir = 'Up';
                    break;
                case 'D':
                    y = x + 3;
                    dir = 'Down';
                    break;
                case 'L':
                    y = x - 1;
                    dir = 'Left';
                    break;
                case 'R':
                    y = x + 1;
                    dir = 'Right';
                    break;
                default:
                    break;
            }
            var temp = squares[x];
            squares[x] = squares[y];
            squares[y] = temp;
            this.setState({ squares: squares, tilePos: y, step: this.state.step + 1, direction: dir });
        }
        else {
            this.setState({ direction: '' });
            clearInterval(this.state.intervalId);
        }
    }

    render() {

        return (
            <View>
                <View style={this.props.solution.errorCode == 0 ? styles.game : styles.hideComp} >
                    <GameBoard squares={this.state.squares} direction={this.state.direction} />
                    <View style={styles.gameInfo}>
                        <Text style={styles.gameInfoText}>No. of Moves: {this.props.solution.path.length}</Text>
                        <Text style={styles.gameInfoText}>No. of Nodes Explored: {this.props.solution.exploredCount}</Text>
                    </View>
                </View>
                <View style={this.props.solution.errorCode == 0 ? styles.hideComp : styles.errorComp}>
                    <Text style={styles.errorText}>{this.state.errorString}</Text>
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
                    <View style={this.props.direction != '' ? styles.directionSquare : { opacity: 0 }}>
                        <Text style={styles.directionText}>{this.props.direction}</Text>
                    </View>
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
            <View style={this.props.value != 0 ? styles.gameSquare : styles.gameBlankSquare}>
                <Text style={styles.gameSquareText}>{this.props.value}</Text>
            </View>
        );
    }
}


/**
 * Styles
 */
const styles = StyleSheet.create({
    hideComp: {
        opacity: 0,
        width: 0,
        height: 0
    },
    game: {
        margin: 10,
        justifyContent: 'center',
    },
    gameBoard: {
        margin: 5,
        paddingTop: 15,
    },
    gameBoardRow: {
        flexDirection: "row",
    },
    gameSquare: {
        backgroundColor: '#379b78',
        width: 75,
        height: 75,
        borderWidth: 1,
        borderRadius: 10,
        borderColor: '#ffffff',
        alignItems: "center",
    },
    gameBlankSquare: {
        width: 75,
        height: 75,
        opacity: 0,
        borderRadius: 10,
    },
    gameSquareText: {
        margin: 13,
        fontSize: 30,
        color: '#ffffff'
    },
    gameInfo: {
        marginTop: 7,
    },
    gameInfoText: {
        fontSize: 15,
        color: '#1a4c63',
        fontFamily: 'Baskerville',
    },

    directionSquare: {
        marginLeft: 20,
        marginTop: 8,
        backgroundColor: '#88cea5',
        width: 75,
        height: 60,
        borderWidth: 1,
        borderRadius: 10,
        borderColor: '#ffffff',
        alignItems: "center",
    },
    directionText: {
        margin: 13,
        fontSize: 18,
        color: '#135940',
        fontFamily: 'Baskerville',
    },

    errorComp: {
        margin: 20,
        alignItems: 'center',
    },
    errorText: {
        padding: 10,
        fontSize: 20,
        color: '#1a4c63',
        fontFamily: 'Baskerville',
    },

});