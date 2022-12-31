<template>
    <div class="row">
        <div class="col">
            <div class="row">
                <div v-for="file in files" class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">{{ file.name }}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">version: {{ file.version }}</h6>
                            <h6 class="card-subtitle mb-2 text-muted">Uploaded on: {{ file.uploadDate }}</h6>
                            <p class="card-text">Url: {{ file.url }}</p>
                            <button v-on:click="deleteFile(file.id)" type="button" class="btn btn-link">Delete</button>

                            <button v-on:click="analyseFile(file.id, file.name)" type="button" class="btn btn-link">Analyse</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col border">
            <div class="row">
                <p class="text-center">Analysis result will apear here</p>
                    <div class="row">
                        <div class="col-5">
                            <b>{{ analysingMSG }}</b>
                        </div>
                        <div v-if="analysing == true" class="col-3">
                            Loading ... 
                        </div>
                    </div>
                    <div v-for="(key, value) in clones" class="row border">
                        <div class="col">{{ value }}</div>
                        <div class="col">{{ key }}%</div>
                    </div>
                
            </div>
        </div>
    </div>
</template>
  
<script>
    const apiURL = "/api/files/get";
    export default{
        name: "FileList",
        data(){
            return {
                files: [],
                id : '',
                
                analysing: false,
                analysingMSG : null,
                clones: null,
            }
        },
        methods:{
            deleteFile(value){
                this.id = value;
                console.log("delete:" + value);

                fetch('api/files/delete', {
                    method: 'POST',
                    body: this.id
                } )
                .then( function( response ){
                    if( response.status != 201 ){
                        this.fetchError = response.status;
                    }else{
                        response.json().then( function( data ){
                            this.fetchResponse = data;
                        }.bind(this));
                    }
                }.bind(this));

                window.location.reload()
            },

            analyseFile(value, name){

                this.id = value;
                this.analysingMSG = " Analysing : " + name;
                this.analysing = true;

                console.log("analyse:" + value);

                fetch('api/analyse/result', {
                    method: 'POST',
                    body: this.id
                } )
                .then( function( response ){
                    if( response.status != 200 ){
                        this.fetchError = response.status;
                    }else{
                        console.log("Got result");
                        response.json().then( function( data ){
                            this.clones = data;
                            this.analysing = false;
                        }.bind(this));
                    }
                }.bind(this));
            },
        },  
        mounted(){
            fetch(apiURL)
            .then((response) => { return response.json(); })
            .then(files => { this.files = files; })
        }
    }
</script>
  
<style>
.card{
    width:250px;
}
</style>