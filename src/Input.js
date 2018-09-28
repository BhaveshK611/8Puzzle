import React, { Component } from "react";
import {
    View,
    Text,
    StyleSheet,
    TextInput,
} from "react-native";

/**
 * Input Class: To take initial and final input from user
 */

export default class Input extends Component {

    render() {

        return (
            <View style={styles.input}>
                <InputBoard squares={this.props.initialConf} setConf={this.props.setInitialConf} label={"Intial State"} />
                <InputBoard squares={this.props.finalConf} setConf={this.props.setFinalConf} label={"Goal State"} />
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
                <View>
                    <Text style={styles.inputTitleText}>{this.props.label}</Text>
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
            <View style={parseInt(this.state.value) == 0 ? styles.inputBlankSquare : styles.inputSquare}>
                <TextInput
                    style={styles.inputSquareText}
                    onChangeText={(value) => {
                        this.setState({ value: value });
                        this.props.setConf(this.props.index, value);
                    }}
                    value={this.state.value}
                    keyboardType='phone-pad'
                    underlineColorAndroid="transparent"
                />
            </View>
        );
    }
}


/**
 * Styles
 */

const styles = StyleSheet.create({
    input: {
        marginTop: 8,
        justifyContent: 'center',
        flexDirection: "row",
    },
    inputBoard: {
        margin: 15,
        alignItems: 'center',
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
        borderColor: '#ffffff',
        alignItems: "center"
    },
    inputBlankSquare: {
        width: 45,
        height: 45,
        opacity: 0,
        borderRadius: 10,
    },
    inputSquareText: {
        paddingLeft: 15,
        fontSize: 15,
    },
    inputTitleText: {
        marginTop: 5,
        fontSize: 20,
        fontFamily: 'Baskerville',
    },
});