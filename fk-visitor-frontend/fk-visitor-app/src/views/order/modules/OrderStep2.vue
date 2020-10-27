<template>
  <div class="step2-wrapper">
    <div class="video">
      <video ref="video" id="video" width="300"></video>
    </div>
    <div class="take" @click="handleClick">
      <c-icon type="fv-paizhao" ></c-icon>
    </div>
    <div class="print">
      <img :src="imageUrl" class="avatar" width="100%" height="100%" />
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

export default {
  props: {
    file: {
      type: Blob,
      default: null
    }
  },
  components: {
  },
  async created () {
    this.codeReader.getVideoInputDevices()
      .then((videoInputDevices) => {
        this.videoList = videoInputDevices
        const selectedDeviceId = videoInputDevices[0].deviceId
        this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
        })
      })
  },
  data () {
    return {
      codeReader: new BrowserMultiFormatReader(),
      imageUrl: 'data:image/jpeg;base64,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'
    }
  },
  methods: {
    async handleClick () {
      const canvas = document.createElement('canvas')
      const context = canvas.getContext('2d')

      canvas.width = 250
      canvas.height = 250

      context.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height)

      this.imageUrl = canvas.toDataURL('image/jpeg')

      const that = this
      await canvas.toBlob(blob => {
        that.$emit('change', blob)
      })
    }
  }
}
</script>

<style scoped>
.step2-wrapper {
  height: 100%;
  position: relative;
}

.step2-wrapper .video {
  width: 300px;
  height: 225px;
  position: absolute;
  left: 25%;
  top: 50%;
  margin-left: -150px;
  margin-top: -112px;
}

.step2-wrapper .take {
  height: 100px;
  width: 100px;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-top: -50px;
  margin-left: -50px;
  text-align: center;
  border: 1px solid #1090dd;
  border-radius: 50%;
  font-size: 60px;
  line-height: 100px;
  color: #ffffff;
  background: #1090dd;
}

.step2-wrapper .print {
  width: 300px;
  height: 225px;
  position: absolute;
  right: 25%;
  top: 50%;
  margin-right: -150px;
  margin-top: -112px;
}

.step2-wrapper .avatar {
  width: 100%;
  height: 100%;
}

.step2-wrapper .upload-btn {
  display: inline-block;
  height: 50px;
  line-height: 60px;
  font-size: 16px;
  font-weight: bold;
  border-bottom: 1px solid #000;
  width: 100px;
}
</style>
