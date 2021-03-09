<template>
  <div class="keyboard-container">
    <div class="search" v-if="inputValue">{{ inputValue }}</div>
    <div class="search" v-else>{{ placeholder }}</div>
    <div class="item-list">
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('1')">1</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('2')">2</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('3')">3</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleDelete()">
          <c-icon type="fv-qingchu" style="font-size:18px"></c-icon>
        </a>
      </div>
    </div>
    <div class="item-list">
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('4')">4</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('5')">5</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('6')">6</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleEmpty()">
          <c-icon type="fv-shanchu"></c-icon>
        </a>
      </div>
    </div>
    <div class="item-list">
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('7')">7</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('8')">8</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('9')">9</a>
      </div>
      <div class="item">
        <a class="keyboard-btn" @click="handleAdd('0')">0</a>
      </div>
    </div>
    <div class="item-list">
      <div class="btn btn-primary btn-fill" @click="handleSubmit">{{ submitMsg }}</div>
    </div>
  </div>
</template>

<script>

export default {
  props: {
    inputValue: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    submitMsg: {
      type: String,
      default: '确定'
    }
  },
  data () {
    return {

    }
  },
  mounted () {

  },
  methods: {
    handleAdd (param) {
      this.$emit('add', param)
    },
    handleDelete () {
      this.$emit('delete')
    },
    handleEmpty () {
      this.$emit('empty')
    },
    handleSubmit () {
      this.$emit('submit')
    }
  }
}
</script>

<style  scoped lang="less">
.keyboard-container {
  width: 400px;
  margin: auto auto;
  vertical-align: middle;
  margin-top: 24px;

  .search {
    color: #013b84;
    border: 2px solid #013b84;
    padding: 5px 24px;
    height: 50px;
    line-height: 40px;
    border-radius: 8px;
    margin-bottom: 34px;
  }

  .item-list {
    margin-bottom: 24px;
  }

  .item {
    width: 25%;
    display: inline-block;
    padding: 0 5px;

    .keyboard-btn {
      height: 50px;
      line-height: 50px;
      border: 2px solid #013b84;
      display: block;
      text-align: center;
      border-radius: 8px;
      color: #013b84;
    }
  }
}
</style>
