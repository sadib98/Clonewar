import * as Vue from "vue";
import Header from './Components/Header.vue';
import FileList from './Components/FileList.vue';
import PostFilePath from './Components/UploadFile.vue';
import "bootstrap/dist/css/bootstrap.min.css";


const header = Vue.createApp(Header);
header.mount("#header");

Vue.createApp(FileList).mount("#filelist");

const addfile = Vue.createApp(PostFilePath);
addfile.mount("#addfile");