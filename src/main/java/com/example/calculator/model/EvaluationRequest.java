package com.example.calculator.model;

import jakarta.validation.constraints.NotBlank;

/**
 * 式評価リクエストモデル
 */
public class EvaluationRequest {

    @NotBlank(message = "式は必須です")
    private String expression;

    public EvaluationRequest() {
    }

    public EvaluationRequest(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
