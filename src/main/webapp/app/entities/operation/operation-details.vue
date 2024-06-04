<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="operation">
        <h2 class="jh-entity-heading" data-cy="operationDetailsHeading"><span>Operation</span> {{ operation.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Operation Name</span>
          </dt>
          <dd>
            <span>{{ operation.operationName }}</span>
          </dd>
          <dt>
            <span>Http Method</span>
          </dt>
          <dd>
            <span>{{ operation.httpMethod }}</span>
          </dd>
          <dt>
            <span>Endpoint Path</span>
          </dt>
          <dd>
            <span>{{ operation.endpointPath }}</span>
          </dd>
          <dt>
            <span>Request Body Template</span>
          </dt>
          <dd>
            <span>{{ operation.requestBodyTemplate }}</span>
          </dd>
          <dt>
            <span>Api Integration</span>
          </dt>
          <dd>
            <div v-if="operation.apiIntegration">
              <router-link :to="{ name: 'ApiIntegrationView', params: { apiIntegrationId: operation.apiIntegration.id } }">{{
                operation.apiIntegration.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="operation.id"
          :to="{ name: 'OperationEdit', params: { operationId: operation.id } }"
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

<script lang="ts" src="./operation-details.component.ts"></script>
