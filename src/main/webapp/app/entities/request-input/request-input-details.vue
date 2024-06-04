<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="requestInput">
        <h2 class="jh-entity-heading" data-cy="requestInputDetailsHeading"><span>Request Input</span> {{ requestInput.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Input Name</span>
          </dt>
          <dd>
            <span>{{ requestInput.inputName }}</span>
          </dd>
          <dt>
            <span>Input Type</span>
          </dt>
          <dd>
            <span>{{ requestInput.inputType }}</span>
          </dd>
          <dt>
            <span>Data Type</span>
          </dt>
          <dd>
            <span>{{ requestInput.dataType }}</span>
          </dd>
          <dt>
            <span>Test Value</span>
          </dt>
          <dd>
            <span>{{ requestInput.testValue }}</span>
          </dd>
          <dt>
            <span>Default Value</span>
          </dt>
          <dd>
            <span>{{ requestInput.defaultValue }}</span>
          </dd>
          <dt>
            <span>Auto User Value</span>
          </dt>
          <dd>
            <span>{{ requestInput.autoUserValue }}</span>
          </dd>
          <dt>
            <span>Is Encoded</span>
          </dt>
          <dd>
            <span>{{ requestInput.isEncoded }}</span>
          </dd>
          <dt>
            <span>Max Length</span>
          </dt>
          <dd>
            <span>{{ requestInput.maxLength }}</span>
          </dd>
          <dt>
            <span>Min Length</span>
          </dt>
          <dd>
            <span>{{ requestInput.minLength }}</span>
          </dd>
          <dt>
            <span>Min Value</span>
          </dt>
          <dd>
            <span>{{ requestInput.minValue }}</span>
          </dd>
          <dt>
            <span>Max Value</span>
          </dt>
          <dd>
            <span>{{ requestInput.maxValue }}</span>
          </dd>
          <dt>
            <span>Validation Pattern</span>
          </dt>
          <dd>
            <span>{{ requestInput.validationPattern }}</span>
          </dd>
          <dt>
            <span>Is Required</span>
          </dt>
          <dd>
            <span>{{ requestInput.isRequired }}</span>
          </dd>
          <dt>
            <span>Operation</span>
          </dt>
          <dd>
            <div v-if="requestInput.operation">
              <router-link :to="{ name: 'OperationView', params: { operationId: requestInput.operation.id } }">{{
                requestInput.operation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="requestInput.id"
          :to="{ name: 'RequestInputEdit', params: { requestInputId: requestInput.id } }"
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

<script lang="ts" src="./request-input-details.component.ts"></script>
