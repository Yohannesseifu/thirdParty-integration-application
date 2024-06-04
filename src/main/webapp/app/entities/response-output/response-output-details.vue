<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="responseOutput">
        <h2 class="jh-entity-heading" data-cy="responseOutputDetailsHeading"><span>Response Output</span> {{ responseOutput.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Output Name</span>
          </dt>
          <dd>
            <span>{{ responseOutput.outputName }}</span>
          </dd>
          <dt>
            <span>Data Type</span>
          </dt>
          <dd>
            <span>{{ responseOutput.dataType }}</span>
          </dd>
          <dt>
            <span>Response Value Path</span>
          </dt>
          <dd>
            <span>{{ responseOutput.responseValuePath }}</span>
          </dd>
          <dt>
            <span>Response Scope</span>
          </dt>
          <dd>
            <span>{{ responseOutput.responseScope }}</span>
          </dd>
          <dt>
            <span>Core Mapping</span>
          </dt>
          <dd>
            <span>{{ responseOutput.coreMapping }}</span>
          </dd>
          <dt>
            <span>Expected Value</span>
          </dt>
          <dd>
            <span>{{ responseOutput.expectedValue }}</span>
          </dd>
          <dt>
            <span>Operation</span>
          </dt>
          <dd>
            <div v-if="responseOutput.operation">
              <router-link :to="{ name: 'OperationView', params: { operationId: responseOutput.operation.id } }">{{
                responseOutput.operation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="responseOutput.id"
          :to="{ name: 'ResponseOutputEdit', params: { responseOutputId: responseOutput.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./response-output-details.component.ts"></script>
