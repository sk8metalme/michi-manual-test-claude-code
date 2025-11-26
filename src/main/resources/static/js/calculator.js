// 電卓の状態管理
const state = {
    currentDisplay: '0',
    expression: '',
    lastResult: null,
    waitingForOperand: false
};

// DOM要素の取得
const displayElement = document.getElementById('display');
const errorMessageElement = document.getElementById('error-message');

// 初期化
function init() {
    // 数字ボタンのイベントリスナー
    document.querySelectorAll('.number').forEach(button => {
        button.addEventListener('click', handleNumberClick);
    });

    // 演算子ボタンのイベントリスナー
    document.querySelectorAll('.operator').forEach(button => {
        button.addEventListener('click', handleOperatorClick);
    });

    // 小数点ボタンのイベントリスナー
    document.querySelector('.decimal').addEventListener('click', handleDecimalClick);

    // クリアボタンのイベントリスナー
    document.querySelector('.clear').addEventListener('click', handleClearClick);

    // イコールボタンのイベントリスナー
    document.querySelector('.equals').addEventListener('click', handleEqualsClick);
}

// 数字ボタンのクリックハンドラー
function handleNumberClick(event) {
    const number = event.target.dataset.number;

    if (state.waitingForOperand) {
        state.expression += number;
        state.currentDisplay = number;
        state.waitingForOperand = false;
    } else {
        if (state.currentDisplay === '0') {
            state.currentDisplay = number;
            state.expression = number;
        } else {
            state.currentDisplay += number;
            state.expression += number;
        }
    }

    updateDisplay();
    clearError();
}

// 演算子ボタンのクリックハンドラー
function handleOperatorClick(event) {
    const operator = event.target.dataset.operator;

    // 演算子の連続入力を防止
    if (state.waitingForOperand) {
        // 直前の演算子を置き換える
        state.expression = state.expression.slice(0, -3) + ' ' + operator + ' ';
    } else {
        state.expression += ' ' + operator + ' ';
        state.waitingForOperand = true;
    }

    state.currentDisplay = state.expression;
    updateDisplay();
    clearError();
}

// 小数点ボタンのクリックハンドラー
function handleDecimalClick() {
    // 現在の数値に小数点が既に含まれているかチェック
    const lastNumber = state.currentDisplay.split(/[\+\-\*\/]/).pop();
    if (lastNumber.includes('.')) {
        return; // 既に小数点がある場合は何もしない
    }

    if (state.waitingForOperand) {
        state.expression += '0.';
        state.currentDisplay = '0.';
        state.waitingForOperand = false;
    } else {
        state.currentDisplay += '.';
        state.expression += '.';
    }

    updateDisplay();
    clearError();
}

// クリアボタンのクリックハンドラー
function handleClearClick() {
    state.currentDisplay = '0';
    state.expression = '';
    state.lastResult = null;
    state.waitingForOperand = false;

    updateDisplay();
    clearError();
}

// イコールボタンのクリックハンドラー
async function handleEqualsClick() {
    if (!state.expression || state.waitingForOperand) {
        return; // 式が空または演算子で終わっている場合は何もしない
    }

    try {
        // バックエンドAPIを呼び出す
        const result = await evaluateExpression(state.expression);

        if (result.error) {
            // エラーレスポンス
            showError(result.error);
        } else {
            // 成功レスポンス
            state.currentDisplay = result.result.toString();
            state.lastResult = result.result;
            state.expression = result.result.toString();
            state.waitingForOperand = false;
            updateDisplay();
            clearError();
        }
    } catch (error) {
        showError('通信エラーが発生しました');
        console.error('API Error:', error);
    }
}

// バックエンドAPIとの通信
async function evaluateExpression(expression) {
    const response = await fetch('/api/calculator/evaluate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ expression })
    });

    return await response.json();
}

// ディスプレイの更新
function updateDisplay() {
    displayElement.textContent = state.currentDisplay || '0';
}

// エラーメッセージの表示
function showError(message) {
    errorMessageElement.textContent = message;
}

// エラーメッセージのクリア
function clearError() {
    errorMessageElement.textContent = '';
}

// 初期化実行
init();
