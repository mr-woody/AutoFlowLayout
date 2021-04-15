package com.woodys.sampletamplate.sample.modle

class TextEntity(var text: String? = null,var isSelected: Boolean = false){
    override fun toString(): String {
        return "TextEntity(text=$text, isSelected=$isSelected)"
    }
}
