<template>
  <a-dropdown>
    <span class="action global-lang">
      <a-icon type="global" style="font-size: 16px"/>
    </span>
    <a-menu slot="overlay" style="width: 150px;" :selectedKeys="[locale]" @click="SwitchLang">
      <a-menu-item key="zhCN">
        <a rel="noopener noreferrer">
          <span role="img" aria-label="简体中文">🇨🇳</span> 简体中文
        </a>
      </a-menu-item>
      <a-menu-item key="enUS">
        <a rel="noopener noreferrer">
          <span role="img" aria-label="English">🇺🇸</span> English
        </a>
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
// import { mixin as langMixin } from '@/store/i18n-mixin'
export default {
  name: 'LangSelect',
  // mixins: [langMixin],
  data () {
    return {}
  },
  computed: {
    locale () {
      return this.$store.getters.lang
    }
  },
  methods: {
    SwitchLang (row) {
      this.$i18n.locale = row.key
      this.$store.dispatch('setLang', row.key)
    }
  }
}
</script>
