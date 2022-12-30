<template>
    <div>
      <ul>
        <li v-for="file in files" >
            id: {{ file.id }} <br/>
            name: {{ file.name }} <br/>
            version: {{ file.version }} 
            <a :href="'?id=' + file.id">Analyze</a>
        </li>
      </ul>
    </div>
</template>
  
<script>
    const apiURL = "/api/files/get";
    export default{
        name: "FileList",
        data(){
            return {
                files: [],
            }
        },
        mounted(){
            fetch(apiURL)
            .then((response) => { return response.json(); })
            .then(files => { this.files = files; })
        }
    }
</script>
  
<style>

</style>