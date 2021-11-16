package com.carcassonneteam.gloomhavenhelper.models

class Item {
    private var id: String = ""
    private var name: String = ""
    private var description: String = ""
    private var price: String = ""
    private var type: String = ""
    private var state: String = ""

    fun getUid(): String {
        return id
    }

    fun setUid(id: String) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String?) {
        this.name = name!!
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getState(): String {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }


    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }
}
